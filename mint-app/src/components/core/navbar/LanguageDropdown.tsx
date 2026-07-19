import i18n, { I18nLanguage, i18nLanguages } from '@/i18n';
import { useMemo } from 'react';
import CaretDownIcon from '../../icons/CaretDownIcon';
import Button from '../button/Button';
import Dropdown, { DropdownAlign, DropdownItem } from '../dropdown/Dropdown';
import LanguageDropdownIcon from './LanguageDropdownIcon';

type LanguageDropdownProps = {
  className?: string;
  align?: DropdownAlign;
};

const LanguageDropdown = ({ className, align }: LanguageDropdownProps): JSX.Element => {
  const currentCode = i18n.language as I18nLanguage;
  const currentLabel = useMemo<string>(() => i18nLanguages[currentCode], [currentCode]);

  const items = useMemo<DropdownItem[]>(
    () =>
      Object.entries(i18nLanguages).map(([code, label]) => ({
        label: label,
        value: code,
        leftIcon: <LanguageDropdownIcon label={label} code={code as I18nLanguage} />,
      })),
    [],
  );

  const handleChange = (code: I18nLanguage) => {
    if (code === currentCode) return;
    i18n.changeLanguage(code);
  };

  return (
    <Dropdown
      className={className}
      items={items}
      onSelect={handleChange}
      align={align}
      menuContainerClassName='w-[102px] max-lg:w-full'>
      <Button
        className='w-full max-h-[41.6px]'
        leftIcon={<LanguageDropdownIcon label={currentLabel} code={currentCode} />}
        rightIcon={<CaretDownIcon />}
        rightIconStyle='text-comp-icon-default'
        labelContStyle='gap-1'
        labelStyle='text-comp-text-default'
        keepLeftIconStyle>
        {currentLabel}
      </Button>
    </Dropdown>
  );
};

export default LanguageDropdown;
