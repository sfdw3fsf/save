import { Button, ButtonProps } from 'antd';
import clsx from 'clsx';
import { ReactNode } from 'react';

type CustomIconButtonProps = {
  className?: string;
  icon: ReactNode;
  isNoBorder?: boolean;
} & Omit<ButtonProps, 'type'>;

const CustomIconButton = ({ className, icon, isNoBorder: isNoneBorder, ...rest }: CustomIconButtonProps) => {
  return (
    <Button
      type='text'
      icon={icon}
      className={clsx(`${className} ${isNoneBorder ? '!border-none !shadow-none' : ''}`)}
      {...rest}
    />
  );
};

export default CustomIconButton;
