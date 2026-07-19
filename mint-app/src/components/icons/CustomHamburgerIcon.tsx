import { twJoin } from 'tailwind-merge';

type CustomHamburgerIconProps = { open?: boolean };

const CustomHamburgerIcon = ({ open = false }: CustomHamburgerIconProps) => (
  <div className={twJoin('hamburger', open && 'active')}>
    <span></span>
    <span></span>
    <span></span>
  </div>
);

export default CustomHamburgerIcon;
