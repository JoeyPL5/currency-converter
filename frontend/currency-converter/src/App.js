import './App.css'
import currencyconversionlogo from './images/currencyconversionlogo.png'
import Conversion from './conversion'

function App () {
  return (
    <div className='App'>
      <header className='App-header'>
        <p color='black'>Joseph Pierre-Louis's Currency Converter</p>

        <img
          id='logo'
          src={currencyconversionlogo}
          className='App-logo'
          alt='logo'
        />
      </header>
      <div className='Conversion-buttons'>
        <Conversion />
      </div>
    </div>
  )
}

export default App
