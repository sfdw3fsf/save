import Dot, { DotProps } from '../dot/Dot';

const StepperDivider = ({ color }: DotProps): JSX.Element => {
  return (
    <div className='flex items-center justify-center w-fit gap-1'>
      {[...Array(3)].map((_, index) => (
        <Dot key={index} color={color} />
      ))}
    </div>
  );
};

export default StepperDivider;
