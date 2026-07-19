import { UploadOutlined } from '@ant-design/icons';
import { Button, Upload, UploadFile } from 'antd';
import { UploadProps } from 'antd/lib';
import { useTranslation } from 'react-i18next';

type CustomFileUploadProps = {
  value?: UploadFile<any>[];
  onChange?: (files: UploadFile<any>[]) => void;
  multiple?: boolean;
  title?: boolean;
  displayFiles?: boolean;
} & Omit<UploadProps, 'fileName' | 'onChange'>;

const CustomFileUploadInput = ({
  value,
  onChange,
  multiple,
  maxCount,
  title = true,
  displayFiles = true,
}: CustomFileUploadProps) => {
  const { t: tCommon } = useTranslation('common');
  const handleUploadChange: UploadProps['onChange'] = ({ fileList }) => {
    if (onChange) {
      onChange(fileList);
    }
  };

  return (
    <Upload
      fileList={displayFiles ? value : []}
      beforeUpload={() => false}
      listType='text'
      maxCount={maxCount}
      onChange={handleUploadChange}>
      <Button icon={<UploadOutlined className='p-2' />}>{title && tCommon('button.upload')}</Button>
    </Upload>
  );
};

export default CustomFileUploadInput;
