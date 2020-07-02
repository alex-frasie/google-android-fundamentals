package ro.utcn.sd.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import ro.utcn.sd.model.CarPart;
import ro.utcn.sd.model.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.io.FileOutputStream;

@Component
public class PDFGenerator {

    /**
     * Generates a PDF resembling a validation for creating a bill after placing an order.
     * @param carParts is the customer which placed the order
     * @param order is the order placed
     * @return the name of the bill
     */

    public String generateBill(Order order, List<CarPart> carParts){
        try {
            Document document = new Document();

            String fileName = "Bill_" + order.getCustomer().getLastName() + "_" + order.getCustomer().getFirstName() + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Font fontItalic = FontFactory.getFont(FontFactory.COURIER, 16, Font.ITALIC);

            Chunk chunk = new Chunk("Hello, Mr/Mrs " + order.getCustomer().getLastName() + " " + order.getCustomer().getFirstName() + ",", fontItalic);
            document.add(new Phrase(chunk));
            document.add(new Phrase("\n\n"));

            chunk = new Chunk("This is the bill representing your payment to CarPart Warehouse.", font);
            document.add(new Phrase(chunk));
            document.add(new Phrase("\n"));

            chunk = new Chunk("The payment of " + order.getTotalAmount().toString() + " was made on " + convertDateToString(order.getPlacementDate()) + ".", font);
            document.add(new Phrase(chunk));
            document.add(new Phrase("\n\n"));

            chunk = new Chunk("You have bought :", font);
            document.add(new Phrase(chunk));
            document.add(new Phrase("\n"));

            for(CarPart carPart : carParts){
                chunk = new Chunk(" - " + carPart.getName() + " (produced by: " + carPart.getProducer().getName() + ")", font);
                document.add(new Phrase(chunk));
                document.add(new Phrase("\n"));
            }
            document.add(new Phrase("\n"));

            chunk = new Chunk("The products will be delivered at:", font);
            document.add(new Phrase(chunk));
            document.add(new Phrase("\n"));
            chunk = new Chunk(order.getCustomer().getAddress().prettyAddress(), fontItalic);
            document.add(new Phrase(chunk));
            document.add(new Phrase("\n\n"));

            chunk = new Chunk("Thank you for using our services!", font);
            document.add(new Phrase(chunk));
            document.add(new Phrase("\n\n"));

            chunk = new Chunk("Sincerely,", fontItalic);
            document.add(new Phrase(chunk));
            document.add(new Phrase("\n"));

            chunk = new Chunk("CarPart Warehouse", fontItalic);
            document.add(new Phrase(chunk));

            document.close();

            return fileName;

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public String convertDateToString(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return dateTime.format(formatter);
    }

}
