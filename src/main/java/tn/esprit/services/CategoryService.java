package tn.esprit.services;

import tn.esprit.models.Category;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategoryService {
    Connection cnx = DBconnection.getInstance().getCnx();
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();

            String query = "SELECT * FROM category";
            PreparedStatement statement = cnx.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("id");
                String categoryName = resultSet.getString("name");

                Category category = new Category(categoryId, categoryName);
                categories.add(category);
            }


        return categories;
    }

}
