import { I18nLanguage } from '@/i18n';
import { ICONS } from '@/utils/theme';
import { twMerge } from 'tailwind-merge';

type LanguageDropdownIconProps = {
  code: I18nLanguage;
  label: string;
  className?: string;
};

const LanguageDropdownIcon = ({ label, code, className }: LanguageDropdownIconProps): JSX.Element => {
  return (
    <div
      className={twMerge(
        'h-6 w-6 shrink-0 rounded-full flex items-center justify-center overflow-hidden border-[0.75px] border-slate-200',
        className,
      )}>
      <img alt={label} className='h-auto w-full' role='presentation' src={ICONS.FLAG[code]} />
    </div>
  );
};

export default LanguageDropdownIcon;
