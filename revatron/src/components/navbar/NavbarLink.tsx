import { ReactNode } from "react";
import { NavLink } from "react-router-dom";
import { twMerge } from "tailwind-merge";

type Props = {
  to: string;
  className?: string;
  children: ReactNode;
};

function NavbarLink({ to, className, children }: Props) {
  return (
    <NavLink
      to={to}
      className={({ isActive }) =>
        twMerge(
          "text-base font-semibold text-warm-600 transition-all duration-300 ease-in-out hover:text-warm-500",
          className,
          isActive && "text-primary-500",
        )
      }>
      {children}
    </NavLink>
  );
}

export default NavbarLink;
