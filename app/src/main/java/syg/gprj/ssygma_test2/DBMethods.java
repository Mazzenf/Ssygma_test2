package syg.gprj.ssygma_test2;


import android.os.StrictMode;

import java.sql.*;


public class DBMethods
{
    DBConnection connectNow = new DBConnection();

    PreparedStatement preparedStatement =null;
    ResultSet resultSet = null;
    Connection connectDB;

    public DBMethods ()
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connectDB = connectNow.getConnection();


    }




    public void populatecb (String toSearchFor) throws SQLException
    {
        //resultSet=connectDB.createStatement().executeQuery("SELECT DISTINCT manufacturer FROM vehicle");
        resultSet=connectDB.createStatement().executeQuery(toSearchFor);
    }




    public void returnFullSearch(String manu,String make, String sortby) throws SQLException {
        preparedStatement=connectDB.prepareStatement("SELECT * FROM vehicle WHERE manufacturer =? and make =?  " + sortby);
        preparedStatement.setString(1, manu);
        preparedStatement.setString(2, make);
        //preparedStatement.setInt(3, model);
        // preparedStatement.setInt(4, capacity);
        // preparedStatement.setInt(5, start_price);
        resultSet= preparedStatement.executeQuery();

    }


    public void returnManuSearch (String manu, String sortby) throws SQLException {
        preparedStatement=connectDB.prepareStatement("SELECT * FROM vehicle WHERE manufacturer =?  " + sortby);
        preparedStatement.setString(1, manu);
        resultSet= preparedStatement.executeQuery(); //ORDER BY start_price ASC
    }

    public void autoSearch () throws SQLException {
        resultSet=connectDB.createStatement().executeQuery("SELECT fullVName FROM vehicle");
    }


    public void returnAvailableVehicle(String status, String sortby) throws SQLException {


        preparedStatement = connectDB.prepareStatement("SELECT * FROM vehicle WHERE vehicle_status =?  "+ sortby ,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        preparedStatement.setString(1, status);
        resultSet= preparedStatement.executeQuery();

//            resultSet.last();
////            numOfRows=resultSet.getRow();
////            System.out.println(numOfRows);
//            resultSet.beforeFirst();
//            while (resultSet.next())
//            {
//
//
//                id = resultSet.getInt(1);
//                plateNum = resultSet.getString(2);
//                manu1 = resultSet.getString(3);
//                MAKE = resultSet.getString(4);
//                model1 = resultSet.getInt(5);
//                capacity = resultSet.getInt(6);
//                price1 = resultSet.getInt(7);
//                insurance = resultSet.getString(8);
//                status = resultSet.getString(9);
//                imgx = resultSet.getString(10);
//
//                System.out.println(id + "  " + plateNum + "  " + manu1 + "  " + MAKE + "  " + model1 + "  " + capacity + "  " + price1 + "  " + insurance + "  " + status + "  " + imgx + resultSet.getRow());
//
//                Image img2 = new Image(getClass().getResourceAsStream(imgx));
//
//                //getData(manu1,MAKE,model1,price1,imgx);
//
//
//                id1.setText(id+"");
//                plate1.setText(plateNum+"");
//                man1.setText(manu1);
//                mak1.setText(MAKE);
//                mod1.setText(model1+"");
//                cap1.setText(capacity+"");
//                sp1.setText(price1+"");
//                insu1.setText(insurance+"");
////                st1.setText(status);
////                img1.setImage(img2);
//
//            }


    }

//
//        try
//    {
//
//
//        preparedStatement = connectDB.prepareStatement("SELECT * FROM vehicle WHERE vehicle_status =?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//        preparedStatement.setString(1, "available");
//        resultSet= preparedStatement.executeQuery();
//
//        resultSet.last();
//        numOfRows=resultSet.getRow();
//        System.out.println(numOfRows);
//        resultSet.beforeFirst();
//        while (resultSet.next())
//        {
//
//
//            id = resultSet.getInt(1);
//            plateNum = resultSet.getString(2);
//            manu1 = resultSet.getString(3);
//            MAKE = resultSet.getString(4);
//            model1 = resultSet.getInt(5);
//            capacity = resultSet.getInt(6);
//            price1 = resultSet.getInt(7);
//            insurance = resultSet.getString(8);
//            status = resultSet.getString(9);
//            imgx = resultSet.getString(10);
//
//            System.out.println(id + "  " + plateNum + "  " + manu1 + "  " + MAKE + "  " + model1 + "  " + capacity + "  " + price1 + "  " + insurance + "  " + status + "  " + imgx + resultSet.getRow());
//
//            Image img2 = new Image(getClass().getResourceAsStream(imgx));
//
//            //getData(manu1,MAKE,model1,price1,imgx);
//
//
//            id1.setText(id+"");
//            plate1.setText(plateNum+"");
//            man1.setText(manu1);
//            mak1.setText(MAKE);
//            mod1.setText(model1+"");
//            cap1.setText(capacity+"");
//            sp1.setText(price1+"");
//            insu1.setText(insurance+"");
//            st1.setText(status);
//            img1.setImage(img2);
//
//
//
//        }
//
//
//
//    }
//        catch (
//    SQLException e)
//    {
//        e.printStackTrace();
//    }
//        finally
//    {
//        connectNow.closeConnection(resultSet,preparedStatement,connectDB);
//    }


    public void OrderSearch (String toSearch) throws SQLException
    {
        preparedStatement=connectDB.prepareStatement("SELECT manufacturer, make, vehicle_image, capacity, start_price  FROM vehicle WHERE fullVName =?");
        preparedStatement.setString(1, toSearch);
        resultSet= preparedStatement.executeQuery();
    }

    public void Tablesearch (String toSearch) throws SQLException
    {
        preparedStatement=connectDB.prepareStatement("SELECT vehicle_id, manufacturer ,make, fullVName, start_price, model,capacity," +
                "insurance_type, plate_number ,vehicle_status  FROM vehicle WHERE fullVName =?");
        preparedStatement.setString(1, toSearch);
        resultSet= preparedStatement.executeQuery();
    }



    public void CloseConnection ()
    {
        connectNow.closeConnection(resultSet,preparedStatement,connectDB);
    }

    public void idek1 (String query1) throws SQLException
    {
        Statement st = connectDB.createStatement();
        resultSet=st.executeQuery(query1);
    }


}
