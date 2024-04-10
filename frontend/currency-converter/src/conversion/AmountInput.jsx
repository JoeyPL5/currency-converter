import React, { useState } from 'react'

function AmountInput ({ onAmountChange }) {
  const [value, setValue] = useState('')

  const handleChange = event => {
    const inputValue = event.target.value
    const regex = /^\d+(\.\d{0,2})?$/
    if (inputValue === '' || regex.test(inputValue)) {
      setValue(inputValue)
      onAmountChange(inputValue)
    }
  }

  return (
    <input className='amount-input'
      type='text'
      value={value}
      onChange={handleChange}
      placeholder={'0.00'}
    />
  )
} export default AmountInput;
