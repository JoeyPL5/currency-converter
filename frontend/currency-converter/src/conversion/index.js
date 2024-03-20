import { useState } from 'react'
import AmountInput from './AmountInput'
import ConversionButton from './ConversionButton'
import SelectableCurrency from './SelectableCurrency'
import './styles.css'

function Conversion () {
  const [currencyFrom, setCurrencyFrom] = useState('')
  const [currencyTo, setCurrencyTo] = useState('')
  const [amount, setAmount] = useState('')
  const [output, setOutput] = useState('')

  const handleCurrencyFromChange = selectedValue => {
    setCurrencyFrom(selectedValue)
  }

  const handleCurrencyToChange = selectedValue => {
    setCurrencyTo(selectedValue)
  }

  const handleAmountChange = value => {
    setAmount(value)
  }

  const handleConversionOutput = output => {
    setOutput(output)
  }

  return (
    <div>
      <div className='conversion-container'>
        <SelectableCurrency onSelectCurrency={handleCurrencyFromChange} />
        <AmountInput onAmountChange={handleAmountChange} />
        <SelectableCurrency onSelectCurrency={handleCurrencyToChange} />
      </div>
      <ConversionButton
        currencies={[currencyFrom, currencyTo]}
        amount={amount}
        onConversion={handleConversionOutput}
      />
      <p className='output-text'>
        {output}
      </p>
    </div>
  )
}
export default Conversion
