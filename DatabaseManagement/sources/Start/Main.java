package Start;

import businessLayer.ClientBLL;
import businessLayer.MainBLL;
import businessLayer.OrderBLL;
import businessLayer.ProductBLL;
import com.itextpdf.text.DocumentException;
import model.*;
import presentation.FileManipulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * We call a FileManipulator to handle our file
 */
public class Main {
    public static void main (String[] args) throws FileNotFoundException, NoSuchFieldException, IllegalAccessException, SQLException, DocumentException {
        FileManipulator fm = new FileManipulator(args[0]);
    }
}
