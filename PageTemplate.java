package others;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PageTemplate implements Serializable{
    protected String file_;
    protected String selected_;
    protected TreeView tv_;
    protected List<String> headerInclude_;
    protected List<String> footerInclude_;
    protected List<String> jsp_servlet_include_;
    protected String scripts;
    protected boolean checkLogin;
    protected String titleH_;
    protected boolean isAdmin_;

    public PageTemplate(String file, String selected_, TreeView tv_, List<String> headerInclude_, List<String> footerInclude_, 
            List<String> jsp_servlet_include_, String scripts, boolean checkLogin, String titleH, boolean isAdmin) {
        this.setFile_(file);
        this.setSelected_(selected_);
        this.setTv_(tv_);
        this.setHeaderInclude_(headerInclude_);
        this.setFooterInclude_(footerInclude_);
        this.setJsp_servlet_include_(jsp_servlet_include_);
        this.setScripts(scripts);
        this.checkLogin = checkLogin;
        this.titleH_ = titleH;
        this.isAdmin_ = isAdmin;
    }
    
    public PageTemplate(String file, String selected_, TreeView tv_, List<String> headerInclude_, List<String> footerInclude_, 
            List<String> jsp_servlet_include_, String scripts, boolean checkLogin, String titleH) {
        this(file, selected_, tv_, headerInclude_, footerInclude_, jsp_servlet_include_, scripts, checkLogin, titleH, false);
    }
    
    public boolean isAdmin() {
        return isAdmin_;
    }
    
    private void setFile_(String file_) {
        this.file_ = file_ != null? file_ : "";
    }

    private void setSelected_(String selected_) {
        this.selected_ = selected_ != null? selected_ : "";
    }

    private void setTv_(TreeView tv_) {
        if(tv_ == null) {
            throw new RuntimeException();
        }
        this.tv_ = tv_;
    }
    
    private void setHeaderInclude_(List<String> l) {
        if(l == null) {
            l = new LinkedList<String>();
        }
        this.headerInclude_ = l;
    }

    private void setFooterInclude_(List<String> l) {
        if(l == null) {
            l = new LinkedList<String>();
        }
        this.footerInclude_ = l;
    }

    private void setJsp_servlet_include_(List<String> l) {
        if(l == null) {
            l = new LinkedList<String>();
        }
        this.jsp_servlet_include_ = l;
    }

    private void setScripts(String scripts) {
        this.scripts = scripts != null? scripts : "";
    }

    public boolean isCheckLogin() {
        return checkLogin;
    }

    public String getFile() {
        return file_;
    }

    public String getSelected() {
        return selected_;
    }

    public TreeView getTV() {
        return tv_;
    }
    
    public String getTitle() {
        return tv_.toStringTitle();
    }

    public List<String> getHeaderInclude() {
        return headerInclude_;
    }

    public List<String> getFooterInclude() {
        return footerInclude_;
    }

    public List<String> getJsp_servlet_include() {
        return jsp_servlet_include_;
    }

    public String getScripts() {
        return scripts;
    }
    
    public String getTitleH() {
        return titleH_;
    }
 
}
