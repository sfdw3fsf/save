import CustomHamburgerIcon from '@/components/icons/CustomHamburgerIcon';
import { useNavigate } from 'react-router-dom';
import Button from '../button/Button';
import Dropdown, { DropdownItem } from '../dropdown/Dropdown';

const NavbarMenu = ({ className, items = [] }: { className?: string; items?: DropdownItem[] }): JSX.Element => {
  const navigate = useNavigate();

  return (
    <Dropdown
      icons={{
        open: <Button className='size-10' variant='secondaryGray' leftIcon={<CustomHamburgerIcon open />} />,
        close: <Button className='size-10' variant='secondaryGray' leftIcon={<CustomHamburgerIcon />} />,
      }}
      className={className}
      items={items}
      onSelect={(link) => navigate(link)}
      align='left'
      menuContainerClassName='pt-6 w-[318px]'
      menuAlign='right'
      menuClassName='gap-2 py-6 flex flex-col'
    />
  );
};

export default NavbarMenu;
