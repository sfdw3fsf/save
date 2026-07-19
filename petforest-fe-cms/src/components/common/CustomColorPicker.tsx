import { ColorPicker } from 'antd';
import { useRef } from 'react';

type CustomColorPickerProps = {
  value: string;
  onChange: (color: string) => void;
  disabled?: boolean;
};

const CustomColorPicker = ({ value, onChange, disabled }: CustomColorPickerProps) => {
  const colorRef = useRef<string>();
  return (
    <ColorPicker
      defaultValue={value}
      onChangeComplete={(color) => {
        const updatedColor = color.toHexString();
        if (updatedColor) {
          colorRef.current = updatedColor;
        }
      }}
      onOpenChange={(open) => {
        if (!open && colorRef.current) {
          onChange(colorRef.current);
        }
      }}
      showText
      disabled={disabled}
    />
  );
};

export default CustomColorPicker;
