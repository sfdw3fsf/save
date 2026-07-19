import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { Card, CardBody, Divider } from '@chakra-ui/react';
import { useMemo } from 'react';
import LineInformation from '../../../../../components/common/line-information/LineInformation';

type GuardiantInformationProps = {
  data?: ObituaryDTO;
};

const GuardiantInformation = ({ data }: GuardiantInformationProps) => {
  const DATA = useMemo(() => {
    return [
      { label: '이름', value: data?.guardianInformation.name },
      { label: '연락처', value: data?.guardianInformation.contact },
      { label: '장례 일시', value: data?.guardianInformation.funeralDate },
      { label: '장례 식장', value: data?.guardianInformation.branch },
      { label: '위치', value: data?.guardianInformation.location },
    ];
  }, [data]);
  return (
    <Card className='w-full mt-[25px] px-[15px] py-[30px] text-[14px] font-medium rounded-[18px]'>
      <CardBody>
        <div className='flex justify-between'>
          <div className='font-bold'>보호자 정보</div>
          <div className='text-[10px] text-grey-mist'>자동 작성 항목</div>
        </div>

        <div className='w-full mt-[18px]'>
          <LineInformation data={DATA.slice(0, 2)} />
        </div>
        <Divider className='mt-3' />
        <div className='font-bold mt-[24px]'>장례 정보</div>
        <div className='w-full mt-[18px]'>
          <LineInformation data={DATA.slice(2, 5)} />
        </div>
      </CardBody>
    </Card>
  );
};

export default GuardiantInformation;
