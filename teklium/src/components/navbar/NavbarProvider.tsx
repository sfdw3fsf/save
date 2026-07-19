import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import Navbar from './Navbar';

export default function NavbarProvider() {
  const location = useLocation();
  const variant = location.pathname;
  return (
    <>
      <Navbar variant={variant}></Navbar>
      <Outlet />
    </>
  );
}
