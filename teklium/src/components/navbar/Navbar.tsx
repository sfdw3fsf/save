import NavbarLink from './NavbarLink';
import Button from '../button/Button';
import QuestionIcon from '../icons/QuestionIcon';
type NavbarProps = {
  variant?: string;
};
export type NavbarVariant = 'normal' | 'transparent';

export default function Navbar({ variant }: NavbarProps) {
  console.log(variant);
  return (
    <div className='fixed top-0 h-navbar w-full px-48 py-6 max-lg:px-4 max-lg:h-16  bg-gray-200 z-20 flex items-center justify-between'>
      <div className='flex gap-3'>
        <div className='text-base text-gray-25 font-medium'>TEKLIUM INC.</div>
        <div className='flex items-center gap-4'>
          <NavbarLink to={'aboutUs'}>About Us</NavbarLink>
          <NavbarLink to={'newAndInsights'}>News & Insights </NavbarLink>
          <NavbarLink to={''}>Revolutionary Technologies</NavbarLink>
          <NavbarLink to={''}>Hydrogen Battery</NavbarLink>
          <NavbarLink to={''}>The Carbon Alliance</NavbarLink>
        </div>
      </div>
      <div className='flex flex-row justify-center gap-3 items-center '>
        <QuestionIcon className='text-gray-500' />
        <div className='max-h-[40px]'>
          <img className='h-[40px]' src={'/bigPlot.png'} alt='Big Plot Image' />
        </div>
        <Button variant='red' className='font-normal h-[40px]'>
          Explore “The Big Plot”
        </Button>
      </div>
    </div>
  );
}
