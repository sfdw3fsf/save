export const helperMappingPlaceColor = (value: number) => {
  return value >= 30 ? 'orange' : value < 10 ? 'red' : 'blue';
};

export const helperMappingPlaceColorBackGround = (value: number) => {
  return value >= 30 ? 'bg-orange-900' : value < 10 ? 'bg-error-500' : 'bg-blue-500';
};
