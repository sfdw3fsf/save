import { Dayjs } from 'dayjs';
import { isNil } from 'lodash';

export const handleBlurUpdate =
  <T extends object>(
    id: number,
    fieldName: keyof T,
    currentValue: T[keyof T],
    onUpdate: (id: number, values: Partial<T>) => void,
    defaultValue?: Partial<T>,
  ) =>
  (e: React.FocusEvent<HTMLInputElement>) => {
    const newValue = e.target.value;
    if (newValue !== (currentValue || '')) {
      onUpdate(id, { [fieldName]: newValue, ...defaultValue } as Partial<T>);
    }
  };

export const handleChangeUpdate =
  <T extends object>(
    id: number,
    fieldName: keyof T,
    currentValue: T[keyof T],
    onUpdate: (id: number, values: Partial<T>) => void,
    defaultValue?: Partial<T>,
  ) =>
  (newValue: T[keyof T]) => {
    if (newValue !== currentValue) {
      onUpdate(id, { [fieldName]: !isNil(newValue) ? newValue : null, ...defaultValue } as Partial<T>);
    }
  };

export const handleChangeDateUpdate =
  <T extends object>(
    id: number,
    fieldName: keyof T,
    onUpdate: (id: number, values: Partial<T>) => void,
    defaultValue?: Partial<T>,
  ) =>
  (newValue: Dayjs | null) => {
    onUpdate(id, { [fieldName]: newValue?.valueOf(), ...defaultValue } as Partial<T>);
  };

export const handleNumericUpdate =
  <T extends object>(
    id: number,
    fieldName: keyof T,
    currentValue: T[keyof T],
    onUpdate: (id: number, values: Partial<T>) => void,
    defaultValue?: Partial<T>,
    converter: (value: number) => any = (value) => value,
  ) =>
  (value: number) => {
    const newValue = converter(value);
    if (newValue !== (currentValue || 0)) {
      onUpdate(id, { [fieldName]: newValue, ...defaultValue } as Partial<T>);
    }
  };
