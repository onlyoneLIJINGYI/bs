package com.ljy.bs.controller;

import com.ljy.bs.entity.Book;
import com.ljy.bs.entity.Rate;
import com.ljy.bs.result.Result;
import com.ljy.bs.result.ResultFactory;
import com.ljy.bs.service.BookService;
import com.ljy.bs.service.RateService;
import com.ljy.bs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*图书业务请求的api
* */
@RestController
public class LibraryController {
    @Autowired
    BookService bookService;
    @Autowired
    RateService rateService;


    //请求资源评论信息（根据资源id，并查询评论的用户信息）
    @CrossOrigin //允许跨域请求的注解
    @GetMapping("/api/resource/{rid}/rate")
    public List<Rate> rateByRid(@PathVariable("rid") int rid) throws Exception {
        return rateService.listAllByRid(rid);
    }

    //请求全部资源评论信息（包括用户信息和资源信息）
    @CrossOrigin //允许跨域请求的注解
    @GetMapping("/api/admin/rate")
    public List<Rate> rateAll( ) throws Exception {
        return rateService.listAll();
    }

    //请求查询所有资源
    @CrossOrigin //允许跨域请求的注解
    @GetMapping("/api/books")
    public List<Book> list() throws Exception {
        return bookService.list();
    }

    //请求添加或修改资源
    @CrossOrigin //允许跨域请求的注解
    @PostMapping("/api/books")
    public Book addOrUpdate(@RequestBody Book book) throws Exception {
        bookService.addOrUpdate(book);
        return book;
    }


    /*api接口由GetMapping和PostMapping来区分请求方式并定义api接口名，
    get请求传递参数可通过@PathVariable来获取请求路径中的变量参数
    Post请求传递的参数可通过@RequestParam来获取
    * */

    @CrossOrigin //允许跨域请求的注解
    @GetMapping("/api/categories/{cid}/resource")
    public List<Book> listByCategory(@PathVariable("cid") int cid) throws Exception {
        if (0 != cid) {
            return bookService.listByCategory(cid);
        } else {
            return list();
        }
    }

    //请求按照关键字查询资源
    @CrossOrigin
    @GetMapping("/api/search")
    public List<Book> searchResult(@RequestParam("keywords") String keywords) throws Exception {
        System.out.println(keywords);
        // 关键词为空时查询出所有书籍
        if ("".equals(keywords)) {
//            return bookService.list();
            return list();
        } else {
            return bookService.Search(keywords);
        }
        /*if ("".equals(keywords)) {
            return ResultFactory.buildSuccessResult(bookService.list());
        } else {
            return ResultFactory.buildSuccessResult(bookService.Search(keywords));
        }*/
    }

    /*请求资源封面url的接口,当上传图片时请求该api，给图片设置网站端口开头命名的url并返回给前台
    * */
    @CrossOrigin
    @PostMapping("api/covers")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = "f:/myProjects/bsproject/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @CrossOrigin //允许跨域请求的注解
    @PostMapping("/api/admin/resource/delete")
    public Result delete(@RequestBody Book book) throws Exception {
        bookService.deleteById(book.getId());
        System.out.println("资源删除成功");
        return ResultFactory.buildSuccessResult("删除成功");
    }


}


