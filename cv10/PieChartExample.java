import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class PieChartExample extends ApplicationFrame {

    public PieChartExample(String title) {
        super(title);
        DefaultPieDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createPieChart(
            "Pie Chart Example",
            dataset,
            true,
            true,
            false
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        setContentPane(chartPanel);
    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Category 1", 30);
        dataset.setValue("Category 2", 40);
        dataset.setValue("Category 3", 20);
        return dataset;
    }

    public static void main(String[] args) {
        PieChartExample example = new PieChartExample("Pie Chart Example");
        example.pack();
        RefineryUtilities.centerFrameOnScreen(example);
        example.setVisible(true);
    }
}
