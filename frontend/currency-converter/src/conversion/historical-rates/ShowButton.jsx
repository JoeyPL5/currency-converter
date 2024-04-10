import React from 'react';

const ShowButton = ({ isVisible, setIsVisible }) => {
  const toggleVisibility = () => {
    setIsVisible(!isVisible); // Toggle the value of isVisible
  };

  return (
    <button onClick={toggleVisibility}>
      {isVisible ? 'Hide Graph' : 'Show Graph'}
    </button>
  );
};

export default ShowButton;