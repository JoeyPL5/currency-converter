import React, { useState, useEffect } from 'react'
import axios from 'axios'
import { BACKEND_URL } from '../Constants'

function SelectableCurrency ({ onSelectCurrency}) {
  const [selectedCurrency, setSelectedCurrency] = useState('')
  const [currencies, setCurrencies] = useState([])

  useEffect(() => {
    axios
      .get(BACKEND_URL + '/currencies')
      .then(response => {
        setCurrencies(Array.from(response.data))
      })
      .catch(error => {
        console.error('Error fetching currencies:', error)
        console.log(BACKEND_URL + '/currencies')
      })
  })

  const handleOptionChange = event => {
    setSelectedCurrency(event.target.value)
    onSelectCurrency(event.target.value)
  }

  return (
    <div>
      <select value={selectedCurrency} onChange={handleOptionChange}>
        <option value=''>Select Currency</option>
        {currencies.map(option => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
    </div>
  )
}
export default SelectableCurrency
