import React, { useState, useEffect } from 'react'
import axios from 'axios'
import { Line } from 'react-chartjs-2'
import { Chart as ChartJS } from 'chart.js/auto' 
import { BACKEND_URL } from '../../Constants'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'

import annotationPlugin from 'chartjs-plugin-annotation';
ChartJS.register(annotationPlugin);

const HistoricalRatesGraph = ( baseCurrency, targetCurrency, isVisible ) => {
  const [historicalRates, setHistoricalRates] = useState({})
  const [startDate, setStartDate] = useState(new Date()) 
  const [endDate, setEndDate] = useState(new Date())

  useEffect(() => {
    console.log(isVisible)
    const fetchData = async () => {
      const response = await axios.get(BACKEND_URL + '/historical-rates', {
        params: {
          start: startDate,
          end: endDate,
          from: baseCurrency,
          to: targetCurrency
        }
      })
      setHistoricalRates(response.data)
    }
    if (isVisible) {
      fetchData();
    }
  }, [isVisible])

  const data = {
    labels: Object.keys(historicalRates),
    datasets: [
      {
        label: `${baseCurrency} to ${targetCurrency}`,
        data: Object.values(historicalRates),
        fill: false,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
      }
    ]
  }

  const options = {
    scales: {
        y: {
          suggestedMin: 0,
          suggestedMax: 2, 
          ticks: {
            stepSize: 1,
          }
        }
      },
    annotation: {
      annotations: {
        line1: {
          type: 'line',
          yMin: 1,
          yMax: 1,
          borderColor: 'red',
          borderWidth: 15,
          label: {
            enabled: true,
            content: '1.00',
            position: 'end'
          }
        },
      },
    },
  };

  return (
    <div>
      <div>
        {isVisible 
        && <DatePicker selected={startDate} onChange={date => setStartDate(date)}/>
        && <DatePicker selected={endDate} onChange={date => setEndDate(date)} /> 
        && <Line data={data} options={options}/> }
      </div>
    </div>
  )
}

export default HistoricalRatesGraph
