import { DownloadOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';
import CustomButton, { CustomButtonProps } from './CustomButton';

const DownloadButton = ({ ...props }: CustomButtonProps) => {
  const { t: tCommon } = useTranslation('common');

  return (
    <CustomButton type={'default'} icon={<DownloadOutlined />} {...props}>
      {tCommon('button.download')}
    </CustomButton>
  );
};

export default DownloadButton;
