import './App.css'
import Conversion from './conversion'

function App () {
  return (
    <div className='App'>
      <header className='App-header'>
        <p className='app-title'>Currency Converter</p>
        <img
          id='logo'
          src='/images/currencyconversionlogo.png'
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
