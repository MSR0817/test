import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EChartsGenerator {

    static class CityData {
        String name;
        int value;

        public CityData(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        List<CityData> data = Arrays.asList(
                new CityData("梅州市", 68),
                new CityData("深圳市", 565),
                new CityData("汕尾市", 23),
                // 其他城市数据...
                new CityData("潮州市", 124)
        );

        // 按值降序排序
        data.sort(Comparator.comparingInt(c -> -c.value));

        // 生成HTML文件
        generateHtmlFile(data, "guangdong-chart.html");

        System.out.println("ECharts HTML文件已生成！");
    }

    private static void generateHtmlFile(List<CityData> data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("    <meta charset=\"utf-8\">\n");
            writer.write("    <title>广东省各城市数据柱状图</title>\n");
            writer.write("    <script src=\"https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js\"></script>\n");
            writer.write("    <style>\n");
            writer.write("        #chart-container {\n");
            writer.write("            width: 900px;\n");
            writer.write("            height: 600px;\n");
            writer.write("            margin: 30px auto;\n");
            writer.write("        }\n");
            writer.write("    </style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("    <div id=\"chart-container\"></div>\n");
            writer.write("    <script>\n");
            writer.write("        var myChart = echarts.init(document.getElementById('chart-container'));\n");

            // 生成JavaScript数据
            writer.write("        var cityNames = [");
            for (int i = 0; i < data.size(); i++) {
                writer.write("\"" + data.get(i).name + "\"");
                if (i < data.size() - 1) writer.write(", ");
            }
            writer.write("];\n");

            writer.write("        var values = [");
            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i).value);
                if (i < data.size() - 1) writer.write(", ");
            }
            writer.write("];\n");

            // ECharts配置
            writer.write("        var option = {\n");
            writer.write("            tooltip: { trigger: 'axis' },\n");
            writer.write("            toolbox: { feature: { saveAsImage: {} } },\n");
            writer.write("            xAxis: { type: 'value', name: '数值' },\n");
            writer.write("            yAxis: { type: 'category', data: cityNames },\n");
            writer.write("            series: [{\n");
            writer.write("                name: '数值',\n");
            writer.write("                type: 'bar',\n");
            writer.write("                data: values,\n");
            writer.write("                itemStyle: { color: '#5470C6' },\n");
            writer.write("                label: { show: true, position: 'right' }\n");
            writer.write("            }]\n");
            writer.write("        };\n");
            writer.write("        myChart.setOption(option);\n");
            writer.write("    </script>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}