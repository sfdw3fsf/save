import { ButtonHTMLAttributes, cloneElement, ReactElement, ReactNode, useCallback } from 'react';
import { twMerge } from 'tailwind-merge';
import Spinner from '../spinner/Spinner';
import { theme } from './button.theme';

export type ButtonSizing = '2sm' | 'sm' | 'md' | 'lg' | 'xl';

export type ButtonVariant = 'primary' | 'secondaryGray' | 'linkColor' | 'link' | 'grayBox';

export type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  children?: ReactNode;
  leftIcon?: ReactElement;
  rightIcon?: ReactElement;
  sizing?: ButtonSizing;
  variant?: ButtonVariant;
  leftIconStyle?: string;
  keepLeftIconStyle?: boolean;
  isLoading?: boolean;
  labelContStyle?: string;
  labelStyle?: string;
  rightIconStyle?: string;
  keepRightIconStyle?: boolean;
};

const Button = ({
  children: label,
  leftIcon,
  rightIcon,
  sizing = 'md',
  variant = 'secondaryGray',
  keepLeftIconStyle = false,
  className,
  type = 'button',
  form,
  isLoading = false,
  onClick,
  disabled = false,
  labelContStyle,
  labelStyle,
  rightIconStyle,
  leftIconStyle,
  keepRightIconStyle = false,
  ...props
}: ButtonProps): JSX.Element => {
  const icon = useCallback(
    (element?: ReactElement, keepIconStyle?: boolean, customStyle?: string) => {
      if (!element) return null;
      return (
        <div className={theme.base.iconContainer}>
          {keepIconStyle
            ? element
            : cloneElement(element, { className: twMerge(theme.base.icon[sizing], theme.variant[variant].color, customStyle) })}
        </div>
      );
    },
    [sizing, variant],
  );

  return (
    <button
      className={twMerge(
        theme.base.buttonCont,
        theme.base.fontSize[sizing],
        theme.variant[variant].base,
        theme.variant[variant].spacing[sizing],
        theme.base.controlHeight[sizing],
        className,
      )}
      form={form ? form : '_notexist'}
      type={form ? 'submit' : type}
      onClick={form ? undefined : onClick}
      disabled={disabled || isLoading}
      {...props}>
      {isLoading && <Spinner className='absolute' variant={variant} sizing={sizing} />}
      <div className={twMerge(theme.base.cont, labelContStyle, isLoading && 'invisible')}>
        {icon(leftIcon, keepLeftIconStyle, leftIconStyle)}
        {!!label && <div className={twMerge(theme.base.content, theme.variant[variant].color, labelStyle)}>{label}</div>}
        {icon(rightIcon, keepRightIconStyle, rightIconStyle)}
      </div>
    </button>
  );
};

export default Button;
