import { ICONS } from '@/theme';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';
import CustomButton, { CustomButtonProps } from './CustomButton';
import CustomIconButton from './CustomIconButton';

type CopyButtonProps = CustomButtonProps & {
  text?: string;
  isIcon?: boolean;
};

const CopyButton = ({ text, onClick, isIcon, ...props }: CopyButtonProps) => {
  const { t: tCommon } = useTranslation('common');

  const onCopy = async (text?: string) => {
    if (!text) return;
    try {
      await navigator.clipboard.writeText(text);
    } catch (error) {
      console.error(error);
    }
  };

  if (isIcon) {
    return (
      <CustomIconButton
        onClick={(e) => {
          onCopy(text);
          onClick?.(e);
        }}
        isNoBorder={true}
        icon={<Image src={ICONS.COPY} alt='Look' width={24} height={24} />}
      />
    );
  }

  return (
    <CustomButton
      type={'default'}
      icon={<Image src={ICONS.COPY} alt='Copy' width={24} />}
      {...props}
      onClick={(e) => {
        onCopy(text);
        onClick?.(e);
      }}>
      {tCommon('button.copy')}
    </CustomButton>
  );
};

export default CopyButton;
