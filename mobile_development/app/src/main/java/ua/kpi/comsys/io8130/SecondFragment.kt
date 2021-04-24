package ua.kpi.comsys.io8130

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlin.math.exp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val standartSwitchCompat = view.findViewById<SwitchCompat>(R.id.switchCompat)

        val functionGraph = view.findViewById<GraphView>(R.id.function)
        this.drawGraph(functionGraph)
        functionGraph.viewport.isXAxisBoundsManual = true
        functionGraph.viewport.isYAxisBoundsManual = true
        functionGraph.viewport.setMinX(-6.0)
        functionGraph.viewport.setMaxX(6.0);
        functionGraph.viewport.setMinY(-6.0)
        functionGraph.viewport.setMaxY(6.0)
        functionGraph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        functionGraph.gridLabelRenderer.isHorizontalLabelsVisible = false
        functionGraph.gridLabelRenderer.isVerticalLabelsVisible = false

        val pieChart = view.findViewById<PieChart>(R.id.pie_chart)
        this.drawPieChart(pieChart)

        standartSwitchCompat?.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) {
                functionGraph.isVisible = false
                pieChart.isVisible = true
            } else {
                functionGraph.isVisible = true
                pieChart.isVisible = false
            }
        }
    }

    fun drawGraph(graphView: GraphView) {
        val series = LineGraphSeries<DataPoint>()
        var x: Double = -6.0
        var y: Double

        while (x != 6.0) {
            x += 0.5
            y = exp(x)
            series.appendData(DataPoint(x, y), true, 100)
        }
        graphView.addSeries(series) //adding e^x
        graphView.addSeries(drawIntersection()) //add line on 1

        val partOfXArrow1 = LineGraphSeries<DataPoint>() //add arrow on x
        partOfXArrow1.appendData(DataPoint(5.5, 0.5), true, 2)
        partOfXArrow1.appendData(DataPoint(6.0, 0.0), true, 2)

        val partOfXArrow2 = LineGraphSeries<DataPoint>()
        partOfXArrow2.appendData(DataPoint(5.5, -0.5), true, 2)
        partOfXArrow2.appendData(DataPoint(6.0, 0.0), true, 2)


        val partOfYArrow = LineGraphSeries<DataPoint>() //add arrow on y
        partOfYArrow.appendData(DataPoint(-0.5, 5.5), true, 3)
        partOfYArrow.appendData(DataPoint(0.0, 6.0), true, 3)
        partOfYArrow.appendData(DataPoint(0.5, 5.5), true, 3)

        //draw y axis
        val yAxis = LineGraphSeries<DataPoint>()
        yAxis.appendData(DataPoint(0.0, -6.0), true, 2)
        yAxis.appendData(DataPoint(0.0, 6.0), true, 2)

        //draw x axis
        val xAxis = LineGraphSeries<DataPoint>()
        xAxis.appendData(DataPoint(-6.0, 0.0), true, 2)
        xAxis.appendData(DataPoint(6.0, 0.0), true, 2)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (resources.configuration.isNightModeActive) {
                partOfXArrow1.color = Color.WHITE
                partOfXArrow2.color = Color.WHITE
                partOfYArrow.color = Color.WHITE
                yAxis.color = Color.WHITE
                xAxis.color = Color.WHITE
            } else {
                partOfXArrow1.color = Color.BLACK
                partOfXArrow2.color = Color.BLACK
                partOfYArrow.color = Color.BLACK
                yAxis.color = Color.BLACK
                xAxis.color = Color.BLACK
            }
        }

        graphView.addSeries(partOfXArrow1)
        graphView.addSeries(partOfXArrow2)
        graphView.addSeries(partOfYArrow)
        graphView.addSeries(yAxis)
        graphView.addSeries(xAxis)

    }
    fun drawIntersection(): LineGraphSeries<DataPoint> {
        val series = LineGraphSeries<DataPoint>()
        var x: Double = -0.5
        var y: Double = 1.0

        for (i in 0..2) {
            series.appendData(DataPoint(x, y), true, 3)
            x += 0.5
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (resources.configuration.isNightModeActive) {
                series.color = Color.WHITE
            } else {
                series.color = Color.BLACK
            }
        }
        return series
    }

    fun drawPieChart(pieChart: PieChart) {
        pieChart.setUsePercentValues(false)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(4.0.toFloat()))
        entries.add(PieEntry(3.0.toFloat()))
        entries.add(PieEntry(3.0.toFloat()))

        val dataSet = PieDataSet(entries, "data")
        val colors = MutableList<Int>(1){Color.BLACK}
        colors.add(Color.rgb(255, 69, 0))
        colors.add(Color.GREEN)
        dataSet.colors = colors

        dataSet.setDrawValues(false)

        val pieData = PieData(dataSet)

        pieChart.data = pieData
        pieChart.legend.isEnabled = false
        pieChart.highlightValues(null)
     //    pieChart.invalidate()

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}