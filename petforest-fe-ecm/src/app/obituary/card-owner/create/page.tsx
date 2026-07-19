'use client';
import { getObituaryApi, obituaryKeys, updateObituaryApi } from '@/store/obituary/obituary.api';

import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { obituaryDtoToReq, obituaryRespToDto } from '@/store/obituary/obituary.service';
import { ObituaryUpdateREQ } from '@/store/obituary/request/obituary.request';
import { orString } from '@/utils/parser/io.parser';
import { useUploadFile } from '@/utils/utils/upload-file-to-s3';
import { keepPreviousData, useMutation, useQuery } from '@tanstack/react-query';
import { Spin } from 'antd';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import '../../obituary.css';
import DonationConfirm from './components/DonationConfirm';
import DonationModal from './components/DonationModal';
import GuardiantInformation from './components/GuardiantInformation';
import PetInformation from './components/PetInformation';
import SuccessModal from './components/SuccessModal';

const page = () => {
  const searchParams = useSearchParams();
  const { uploadFile } = useUploadFile();

  const previousDataRef = useRef<ObituaryDTO | null>(null);

  const [isDonation, setIsDonation] = useState<boolean>(false);
  const [isOpenSuccess, setIsOpenSuccess] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const methods = useForm<ObituaryDTO>();
  const { handleSubmit, reset } = methods;

  const id: string = useMemo(() => {
    return orString(searchParams.get('id'));
  }, [searchParams]);

  const { data: obituaryData } = useQuery({
    queryKey: obituaryKeys.list({ id }),
    queryFn: () => getObituaryApi(id),
    select: (response) => {
      return response.data.data && obituaryRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
    enabled: !!id,
  });

  const { mutate: mutateUpdateObituary } = useMutation({
    mutationFn: ({ id, body }: { id: string; body: ObituaryUpdateREQ }) => updateObituaryApi(id, body),
    onSuccess: () => {
      reset();
      setIsOpenSuccess(true);
    },
    onError(error) {
      alert(((error as any).response.data.message as string[]).map((item) => item));
    },
  });

  const onDonationQuestion = () => {
    setIsDonation(true);
  };

  const validate = (values: ObituaryDTO) => {
    if (!values.petImage) {
      alert('아이 사진은 필수 입니다.');
      return false;
    } else if (!values.petInformation.endDate) {
      alert('아이 망년월일은 필수 입니다.');
      return false;
    } else if (!values.petInformation.age) {
      alert('아이 나이는 필수 입니다.');
      return false;
    }
    return true;
  };

  const onSuccess = async (values: ObituaryDTO) => {
    if (!validate(values)) {
      return;
    }
    setIsLoading(true);
    const fileIds = values.obituaryFileIds && ((await uploadFile({ files: values.obituaryFileIds })) as number[]);
    const petImageId = values.petImage && ((await uploadFile({ files: values.petImage })) as number[]);
    const body = obituaryDtoToReq(values, fileIds, petImageId);
    mutateUpdateObituary({ id: id, body });
    setIsLoading(false);
  };

  useEffect(() => {
    if (!obituaryData) return;

    if (JSON.stringify(previousDataRef.current) !== JSON.stringify(obituaryData)) {
      reset(obituaryData);
      previousDataRef.current = obituaryData;
    }
  }, [obituaryData, reset]);

  return (
    <div className='px-[20px]  pb-[40px] bg-grey-pearl flex flex-col items-center'>
      <div className='h-[72px] w-full bg-white'>
        <img className='logo-green h-[40px] mt-3' src='/images/logo.svg' alt='logo' />
      </div>

      <FormProvider {...methods}>
        <div className='w-full max-w-[480px] mt-[15px]'>
          <div className='font-bold text-[22px]'>
            영원히 기억할게
            <br />
            기억의 숲에서 다시 만나
          </div>
          <GuardiantInformation data={obituaryData} />
          <PetInformation />
          <DonationConfirm onDonationQuestion={onDonationQuestion} />

          <button onClick={handleSubmit(onSuccess)} className='btn-primary-obituary mt-[35px]'>
            {isLoading ? <Spin style={{ color: 'white' }} /> : '부고장 제작 완료'}
          </button>
        </div>
      </FormProvider>

      <DonationModal
        isOpen={isDonation}
        onClose={() => {
          setIsDonation(false);
        }}
      />

      <SuccessModal
        id={id}
        data={obituaryData}
        isOpen={isOpenSuccess}
        onClose={() => {
          setIsOpenSuccess(false);
        }}
      />
    </div>
  );
};

export default page;
