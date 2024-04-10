import { useState } from 'react';
import AmountInput from './AmountInput';
import ConversionButton from './ConversionButton';
import SelectableCurrency from './SelectableCurrency';
import './styles.css';
import ReverseButton from './ReverseButton'; 
import HistoricalRatesGraph from './historical-rates/HistoricalRatesGraph';

function Conversion() {
  const [currencyFrom, setCurrencyFrom] = useState('');
  const [currencyTo, setCurrencyTo] = useState('');
  const [amount, setAmount] = useState('');
  const [outputs, setOutputs] = useState([]);

  const handleAmountChange = value => {
    setAmount(value);
  };

  const handleConversionOutput = output => {
    const newOutputs = [{ value: output, timestamp: Date.now() }, ...outputs];
    setOutputs(newOutputs.slice(0, 3)); // Limit to last 5 outputs
  };

  const handleReverse = () => {
    const temp = currencyFrom;
    setCurrencyFrom(currencyTo);
    setCurrencyTo(temp);
  };

  return (
    <div className='conversion-page'>
      <div className='historical-rates-graphs'>
        <HistoricalRatesGraph baseCurrency={currencyFrom} targetCurrency={currencyTo} isVisible={false}/>
      </div>
      <div className='conversion-buttons-container'>
        <div className='conversion-container'>
          <SelectableCurrency selected={currencyFrom} onSelectCurrency={setCurrencyFrom} />
          <AmountInput onAmountChange={handleAmountChange} />
          <SelectableCurrency selected={currencyTo} onSelectCurrency={setCurrencyTo} />
        </div>
        <div className='conversion-container'>
          <ConversionButton
            currencies={[currencyFrom, currencyTo]}
            amount={amount}
            onConversion={handleConversionOutput}
          />
          <div className='reverse-button'>
            <ReverseButton onReverse={handleReverse} /> 
          </div>
        </div>
        
        <div className='outputs'>
          {outputs.map((output, index) => (
            <p className='output-text' key={index} style={{ opacity: 1 - index * 0.33333, fontSize: `${15 - index}px` }}>
              {output.value}
            </p>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Conversion;
