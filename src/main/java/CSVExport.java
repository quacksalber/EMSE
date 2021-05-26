import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVExport {

    public static void createCSV(List<Long> times, String currentmode) throws IOException {
        String[] header = {"Runde", "1","2","3","4","5","6","7","8","9","10"};
        String[] timeArr = new String[times.size()+1];
        timeArr[0] = " ";

        for(int i = 1; i < timeArr.length;i++){
            timeArr[i] = String.valueOf(times.get(i-1));
        }

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(timeArr);

        //"C:\\Users\\Kevin\\Desktop\\EMSE\\" + currentmode + "_Zeiten.csv"
        String filename = currentmode + "_zeiten.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(new File(".", filename)))) {
            writer.writeAll(list);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
