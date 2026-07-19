import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';

function NavProvider() {
  return (
    <>
      <Navbar />
      <Outlet />
    </>
  );
}

export default NavProvider;
