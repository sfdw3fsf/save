import { ButtonTypeEnum } from '@/utils/enums/button-type.enum';
import { Button, ButtonProps } from 'antd';
import { ButtonType } from 'antd/es/button';
import clsx from 'clsx';
import { ReactNode, useMemo } from 'react';

export type CustomButtonProps = {
  className?: string;
  type?: 'primary' | 'secondary' | 'default' | 'text' | 'ghost';
  children?: ReactNode;
  icon?: ReactNode;
} & Omit<ButtonProps, 'type'>;

const CustomButton = ({ className, children, type = 'default', icon, ...rest }: CustomButtonProps) => {
  const buttonType = useMemo(() => {
    if ((type as ButtonTypeEnum) === ButtonTypeEnum.SECONDARY || (type as ButtonTypeEnum) === ButtonTypeEnum.GHOST) {
      return ButtonTypeEnum.PRIMARY;
    } else {
      return type;
    }
  }, [type]);

  return (
    <Button
      {...rest}
      type={buttonType as ButtonType}
      ghost={type === 'ghost'}
      icon={icon}
      iconPosition={'start'}
      className={clsx(
        `${(type as ButtonTypeEnum) === ButtonTypeEnum.SECONDARY ? '!bg-brand-forest' : ''} ${
          (type as ButtonTypeEnum) === ButtonTypeEnum.GHOST ? '!text-primary-900 !border-primary-900' : ''
        } ${(type as ButtonTypeEnum) === ButtonTypeEnum.DEFAULT ? '!text-grey-dim' : ''} ${className} `,
      )}>
      {children}
    </Button>
  );
};

export default CustomButton;
