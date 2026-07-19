'use client';
import CustomImageUploadInput from '@/components/common/CustomUploadImageInput';
import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { Card, CardBody } from '@chakra-ui/react';
import { DatePicker, Input } from 'antd';
import dayjs from 'dayjs';
import { Controller, useFormContext } from 'react-hook-form';

const PetInformation = () => {
  const { control } = useFormContext<ObituaryDTO>();

  return (
    <Card className='w-full mt-[25px] px-[15px] py-[30px] text-[14px] font-medium rounded-[18px]'>
      <CardBody>
        <div className='flex justify-between'>
          <div className='font-bold'>
            반려동물 정보<em>*</em>
          </div>
          <div className='text-[10px] text-green-main'>
            <em>* </em>필수 입력 사항 입니다
          </div>
        </div>
        <p className='text-[11px] text-grey-mist'>메인 사진은 한장 업로드 가능하며, 세로형 사진 사용을 권장합니다.</p>
      </CardBody>

      <div className='flex w-full mt-4 gap-4 pr-[30px]'>
        <Controller
          name='petImage'
          control={control}
          render={({ field: { onChange, value } }) => (
            <CustomImageUploadInput
              fileType='image'
              className='!w-[90px] !h-[90px]'
              value={value}
              onChange={onChange}
              multiple
              maxCount={1}
            />
          )}
        />

        <div className='flex flex-col justify-between min-h-[90px]'>
          <div className='flex items-center flex-1'>
            <div className='text-[14px] font-medium w-[58px] text-grey-mist'>이름</div>

            <Controller
              name='petInformation.name'
              control={control}
              render={({ field: { onChange, value } }) => (
                <Input value={value} onChange={onChange} type='text' className='input font-normal text-[14px]' />
              )}
            />
          </div>
          <div className='flex items-center flex-1'>
            <div className='text-[14px] font-medium w-[58px] text-grey-mist'>구분</div>
            <Controller
              name='petInformation.petType.name'
              control={control}
              render={({ field: { onChange, value } }) => (
                <Input value={value} onChange={onChange} type='text' className='input font-normal text-[14px]' disabled />
              )}
            />
          </div>
        </div>
      </div>

      <div className='flex justify-between mt-[84px]'>
        <div className='font-bold'>
          반려동물 정보 추가 기입<em>*</em>
        </div>
        <div className='text-[10px] text-green-main'>
          <em>* </em>필수 입력 사항 입니다
        </div>
      </div>

      <div className='mt-[29px] flex flex-col gap-[10px]'>
        <Controller
          name='petInformation.endDate'
          control={control}
          render={({ field: { onChange, value } }) => (
            <DatePicker maxDate={dayjs()} value={value} onChange={onChange} className='input' placeholder='망년월일' />
          )}
        />

        <Controller
          name='petInformation.age'
          control={control}
          render={({ field: { onChange, value } }) => (
            <Input
              value={value}
              onChange={onChange}
              type='text'
              className='input font-normal text-[14px]'
              placeholder='아이 나이를 입력 해주세요'
            />
          )}
        />
        <Controller
          name='petInformation.petBreed.name'
          control={control}
          render={({ field: { onChange, value } }) => (
            <Input
              value={value}
              onChange={onChange}
              type='text'
              className='input font-normal text-[14px]'
              placeholder='아이 품종을 입력 해주세요'
              disabled
            />
          )}
        />
      </div>

      <div className='mt-[30px] mb-3'>
        <div className='font-bold'>
          반려동물 정보 추가 기입<em>*</em>
        </div>
        <p className='text-[11px] mt-1 text-grey-mist'>메인 사진은 한장 업로드 가능하며, 세로형 사진 사용을 권장합니다.</p>
      </div>

      <div className='custom-upload'>
        <Controller
          name='obituaryFileIds'
          control={control}
          render={({ field: { onChange, value } }) => (
            <CustomImageUploadInput fileType='image' value={value} onChange={onChange} multiple maxCount={9} />
          )}
        />
      </div>
    </Card>
  );
};

export default PetInformation;
