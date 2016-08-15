/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.Controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import za.ac.vut.Entity.Category;
import za.ac.vut.Entity.Residence;
import za.ac.vut.Facade.CategoryFacade;
import za.ac.vut.util.Msg;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class CategoryController
{
    
    @EJB
    private CategoryFacade categoryFacade;
    
    private Category category;
    
    private List<Category> categoryList;
    
    private String name, searchInput;
    
    private Msg msg;
    
    @PostConstruct
    public void init()
    {
        categoryList = categoryFacade.findAll();
    }
    
    public CategoryController()
    {
        categoryList = new ArrayList<>();
        
        category = new Category();
        
        msg = new Msg();
    }
    
    public Category getCategory()
    {
        return category;
    }
    
    public String getName()
    {
        return name;
    }
    
    public List<Category> getCategoryList()
    {
        return categoryList;
    }
    
    public void setSearchInput(String searchInput)
    {
        this.searchInput = searchInput;
    }
    
    public String getSearchInput()
    {
        return searchInput;
    }
    
    public void refreshCategories()
    {
        searchInput = "";
        categoryList = categoryFacade.findAll();
    }
    
    public void setCategory(Category category)
    {
        this.category = category;
    }
    
    public void setCategoryList(List<Category> categoryList)
    {
        this.categoryList = categoryList;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void saveCategory()
    {
        if (name.isEmpty())
        {
            msg.error("Please Enter Category Name");
        } else if (name.length() < 3)
        {
            msg.error("Category Name too short it must be 3 characters atleast");
        } else
        {
            category.setCategoryName(name);
            name = "";
            categoryFacade.create(category);
            category = new Category();
            categoryList = categoryFacade.findAll();
            msg.info("New Category Name Saved Successfully");
        }
    }
    
    
      public void onRowCancel(RowEditEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        Category cancelledCategory = (Category) event.getObject();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Changes cancelled", cancelledCategory.getCategoryName()));
        
    }
    
    public void onRowEdit(RowEditEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        Category editCategory = (Category) event.getObject();
        
        if (editCategory.getCategoryName().isEmpty())
        {
            msg.error("Please Enter Category Name");
        } else
        {
            categoryFacade.edit(editCategory);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Category Information editted: ", editCategory.getCategoryName()));
            
        }
        
    }
    
    public void remove(Category myCategory)
    {
        categoryFacade.remove(myCategory);
        categoryList = categoryFacade.findAll();
        msg.info(myCategory.getCategoryName()+ " has been removed");
    }
    

    
    public void searchResidence()
    {
        categoryList.clear();
        for (Category categ : categoryFacade.findAll())
        {
            if (categ.getCategoryName().toLowerCase().contains(searchInput.toLowerCase()))
            {
                categoryList.add(categ);
            }
        }
        
        searchInput = "";
    }
    
    
}
