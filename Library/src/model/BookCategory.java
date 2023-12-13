package model;

public enum BookCategory {
    BIOGRAPHY(1,"Biography"),NOVEL(2,"Novel"),FICTION(3,"Fiction"),THRILLER(4,"Thriller");

    private int id;
    private String name;

    BookCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BookCategory getBy(String name){
        for(BookCategory eCategory : BookCategory.values()){
            if (eCategory.toString().equals(name)) {
                return eCategory;
            }
        }
        return null;
    }
    public static BookCategory getBy(long idCategory){
        for(BookCategory eCategory : BookCategory.values()){
            if (eCategory.getId() == idCategory) {
                return eCategory;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
