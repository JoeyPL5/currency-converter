import React from 'react';

function ReverseButton({ onReverse }) {
  return (
    <button className="reverse-button" onClick={onReverse}>
      <img className="reverse-icon" src="/images/swap-icon.svg" alt="Swap Icon" />
    </button>
  );
}

export default ReverseButton;