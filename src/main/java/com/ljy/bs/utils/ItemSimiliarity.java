package com.ljy.bs.utils;


import com.ljy.bs.entity.Like;
import com.ljy.bs.entity.Book;
import com.ljy.bs.entity.User;
import com.ljy.bs.service.BookService;
import com.ljy.bs.service.LikeService;
import com.ljy.bs.service.PaperService;
import com.ljy.bs.service.UserService;
import java.util.*;

import com.ljy.bs.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemSimiliarity {
    @Autowired
    PaperService paperService;
    /*public void contextLoads() {
        //进行单元测试
        //GoodsServiceImpl为我想要获取的service层中的类
        UserService userService = (UserService)SpringUtil.getBean(UserService.class);
        System.out.println(userService.list());
    }*/

    public static List<Book> recommend(int uid){

        UserService userService = SpringUtil.getApplicationContext().getBean(UserService.class);
        BookService bookService = SpringUtil.getApplicationContext().getBean(BookService.class);
        LikeService likeService = SpringUtil.getApplicationContext().getBean(LikeService.class);
        List<Like> likeLists;//其他用户喜欢的资源列表
        //所有用户
        List<User> users = userService.findAll();
        //所有资源
        List<Book> resourcelist=bookService.list();
        int[][] curMatrix = new int[resourcelist.size()+5][resourcelist.size()+5];   //当前矩阵
        int[][] comMatrix = new int[resourcelist.size()+5][resourcelist.size()+5];   //共现矩阵
        //一维数组，记录喜欢每个资源的人数
        int[] N = new int[resourcelist.size()+5];

        //对所有用户循环，获取每个用户喜欢的资源列表（除去要查询的用户）
        for(User user: users){
            if(user.getId()==uid) continue;                    //当前用户则跳过

            likeLists = likeService.findAllByUid(user.getId()); //循环遍历用户的喜欢列表


            for(int i = 0; i < resourcelist.size(); i++)
                for(int j = 0; j < resourcelist.size(); j++)
                    curMatrix[i][j] = 0;                               //清空矩阵

            for(int i = 0; i < likeLists.size(); i++){
                int pid1 = likeLists.get(i).getPid();
                //将对应资源的数组值加1 ，即喜欢该资源的人数加1
                ++N[pid1];
                for(int j = i+1; j < likeLists.size(); j++){
                    int pid2 = likeLists.get(j).getPid();
                    ++curMatrix[pid1][pid2];
                    ++curMatrix[pid2][pid1]; //两两加一
                }
            }
            //累加所有矩阵, 得到共现矩阵
            for(int i = 0; i < resourcelist.size(); i++){
                for(int j = 0; j < resourcelist.size(); j++){
                    int pid1 = resourcelist.get(i).getId();
                    int pid2 = resourcelist.get(j).getId();
                    comMatrix[pid1][pid2] += curMatrix[pid1][pid2];
                    comMatrix[pid1][pid2] += curMatrix[pid1][pid2];
                }
            }
        }
        //资源推荐权值比较列表
        TreeSet<Book> preList = new TreeSet<Book>(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                //o1.getW()获取资源的相似度进行比较，相似度不同返回相似度值大的资源
                if(o1.getW()!=o2.getW())
                    return (int) (o1.getW()-o2.getW())*100;
                else
                    //资源相似度相同，返回资源喜欢人数多的资源
                    return o1.getLikecnt()-o2.getLikecnt();
            }
        }); //预处理的列表

        likeLists = likeService.findAllByUid(uid);       //当前用户喜欢的资源列表
        boolean[] used = new boolean[resourcelist.size()+5];  //判重数组
        for(Like like: likeLists){
            int Nij = 0;                         //既喜欢i又喜欢j的人数
            double Wij;                          //相似度
            Book tmp;                           //当前的资源

            //对当前用户喜欢的资源列表进行循环遍历，去除掉已经保存的like表中的资源，避免重复推荐
            int i = like.getPid();
            for(Book resource: resourcelist){
                if(like.getPid() == resource.getId()) continue;
                //i为当前用户喜欢的，j为用户未喜欢的，comMatrix[i][j]记录既喜欢i又喜欢j的人数，初始为0（前面初始化过）
                int j = resource.getId();
                Nij = comMatrix[i][j];
                Wij = (double)Nij/Math.sqrt(N[i]*N[j]); //计算余弦相似度，根据推荐算法公式进行计算，即根据用户喜欢程度计算商品的相似度

                //将j资源与用户喜欢资源i的资源相似度值保存
                tmp = bookService.findById(resource.getId());
                tmp.setW(Wij);


                if(used[tmp.getId()]) continue;
                preList.add(tmp);
                used[tmp.getId()] = true;
            }
        }


        ArrayList<Book> recomLists = new ArrayList<>();      //生成的推荐结果
        for(int i = 0; preList.size()>0 && i<5; i++){
            recomLists.add(preList.pollLast());
            preList.pollLast();
        }
       /* if(recomLists.size()<5){
            //推荐数量不满5个, 补足喜欢数最高的文章
            recomLists = paperdao.findTopNPapers(recomLists);
        }*/

        //返回结果不对，避免报错随便写的
        return recomLists;
    }
}
