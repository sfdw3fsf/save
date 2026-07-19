import { DeleteOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';
import CustomButton, { CustomButtonProps } from './CustomButton';

const DeleteButton = ({ ...props }: CustomButtonProps) => {
  const { t: tCommon } = useTranslation('common');

  return (
    <CustomButton type={'default'} icon={<DeleteOutlined />} {...props}>
      {tCommon('button.delete')}
    </CustomButton>
  );
};

export default DeleteButton;
