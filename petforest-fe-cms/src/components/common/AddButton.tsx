import { PlusOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';
import CustomButton, { CustomButtonProps } from './CustomButton';

const AddButton = ({ ...props }: CustomButtonProps) => {
  const { t: tCommon } = useTranslation('common');

  return (
    <CustomButton type={'secondary'} icon={<PlusOutlined />} {...props}>
      {tCommon('button.add')}
    </CustomButton>
  );
};

export default AddButton;
