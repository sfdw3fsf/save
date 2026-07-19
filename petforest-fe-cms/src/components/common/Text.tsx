import { Typography } from 'antd';
import { TextProps } from 'antd/es/typography/Text';
import { ReactNode } from 'react';

type TextCustomProps = {
  children: ReactNode;
  variant?:
    | 'display-1'
    | 'display-2'
    | 'heading-1'
    | 'heading-2'
    | 'headline-1'
    | 'headline-2'
    | 'title-1'
    | 'title-2'
    | 'title-3'
    | 'body-1-normal'
    | 'body-1-reading'
    | 'body-2-normal'
    | 'body-2-reading'
    | 'caption-1'
    | 'caption-2'
    | 'caption-3'
    | 'label-2'
    | 'label-1-reading'
    | 'label-1-normal';
  className?: string;
} & TextProps;

const CustomText = ({ variant = 'display-1', children, className, ...rest }: TextCustomProps) => {
  return (
    <Typography.Text className={`${variant} ${className}`} {...rest}>
      {children}
    </Typography.Text>
  );
};

export default CustomText;
