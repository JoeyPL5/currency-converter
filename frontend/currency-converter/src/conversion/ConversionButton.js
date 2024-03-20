import { BACKEND_URL } from '../Constants'
import axios from 'axios'

function ConversionButton ({ currencies, amount, onConversion }) {
  const [currencyFrom, currencyTo] = currencies

  const handleConversion = () => {
    if (currencyFrom && currencyTo && amount) {
      const query =
        BACKEND_URL +
        '/convert?from=' +
        currencyFrom +
        '&to=' +
        currencyTo +
        '&amount=' +
        amount
      axios
        .get(query)
        .then(response => {
          console.log(response.data)
          onConversion(response.data)
        })
        .catch(error => {
          console.error('Error converting currency:', error)
          console.log(query)
        })
    }
  }

  return (
    <div>
      <button onClick={handleConversion}>Convert</button>
    </div>
  )
}
export default ConversionButton
