package com.ljy.bs.service;

import com.ljy.bs.dao.BookDAO;
import com.ljy.bs.entity.Category;
import com.ljy.bs.entity.Book;
import com.ljy.bs.utils.ItemSimiliarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/*查出所有书籍、增加或更新书籍、通过 id 删除书籍和通过分类查出书籍
save() 方法的作用是，当主键存在时更新数据，当主键不存在时插入数据。。
* */
@Service
public class BookService {
    @Autowired
    BookDAO bookDAO;
    @Autowired
    CategoryService categoryService;

    //推荐资源的方法
    public List<Book> recommendByUid(int uid){
        List<Book> recomLists= ItemSimiliarity.recommend(uid);
        return recomLists;
    }
    //查询所有资源
    public List<Book> list() {
        //按倒序查询
        /*Sort sort = new Sort(Sort.Direction.DESC, "id");
        return bookDAO.findAll(sort);*/
        return bookDAO.findAll();
    }


    public Book findById(int rid) {

        return bookDAO.findById(rid);
    }
    public void addOrUpdate(Book book) {
        bookDAO.save(book);
    }

    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }

    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookDAO.findAllByCategory(category);
    }
    /*按关键字查询数据
    * */
    public List<Book> Search(String keywords) {
        return bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%');
    }

}

