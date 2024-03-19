import './App.css';
import currencyconversionlogo from './images/currencyconversionlogo.png';
import SelectableCurrency from './SelectableCurrency'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p color="black">
          Joseph Pierre-Louis's Currency Converter
        </p>

        <img id="logo" src={currencyconversionlogo} className="App-logo" alt="logo" />
      </header>
      <SelectableCurrency/>
    </div>
  );
}

export default App;
