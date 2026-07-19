import { EditOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';
import CustomButton, { CustomButtonProps } from './CustomButton';

const EditButton = ({ ...props }: CustomButtonProps) => {
  const { t: tCommon } = useTranslation('common');

  return (
    <CustomButton type={'secondary'} icon={<EditOutlined />} {...props}>
      {tCommon('button.edit')}
    </CustomButton>
  );
};

export default EditButton;
