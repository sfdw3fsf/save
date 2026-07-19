import { ButtonHTMLAttributes, cloneElement, ReactElement } from 'react';
import { twMerge } from 'tailwind-merge';
import { theme } from './icon-button.theme';

type IconButtonSizing = 'xs' | 'sm' | 'md';

type IconButtonVariant = 'greyGhost' | 'primary' | 'raw';

export type IconButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  sizing?: IconButtonSizing;
  variant?: IconButtonVariant;
  icon: ReactElement;
  keepIconStyle?: boolean;
  iconClassName?: HTMLDivElement['className'];
};

const IconButton = ({
  icon,
  sizing = 'md',
  variant = 'greyGhost',
  keepIconStyle = false,
  className,
  form,
  type = 'button',
  iconClassName,
  ...props
}: IconButtonProps): JSX.Element => {
  return (
    <button
      type={form ? 'submit' : type}
      form={form ? form : '_notexist'}
      className={twMerge(theme.base, theme.contSize[sizing], theme.cont[variant], theme.defaultContSize[variant], className)}
      {...props}>
      {keepIconStyle
        ? icon
        : cloneElement(icon, {
            className: twMerge(theme.iconSize[sizing], theme.icon[variant], theme.defaultIconSize[variant], iconClassName),
          })}
    </button>
  );
};

export default IconButton;
