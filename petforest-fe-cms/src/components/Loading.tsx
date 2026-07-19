import { LoadingOutlined } from '@ant-design/icons';
import { Spin } from 'antd';
import CustomText from './common/Text';

const Loading = () => {
  return (
    <div className='flex w-screen h-screen items-center justify-center gap-3'>
      <Spin indicator={<LoadingOutlined style={{ fontSize: 36 }} spin />}></Spin>
      <CustomText variant='heading-1' className='!text-primary-1000'>
        Loading
      </CustomText>
    </div>
  );
};

export default Loading;
