'use client';
import { orString } from '@/utils/parser/io.parser';
import { useRouter, useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';
import FuneralInformation from '../components/FuneralInformation';
import Message from '../components/Message';
import Obituary from '../components/Obituary';
import ObituaryImages from '../components/ObituaryImages';
import SharedModal from '../components/SharedModal';

import { getObituaryApi, increaseCountViewApi, obituaryKeys } from '@/store/obituary/obituary.api';
import { obituaryRespToDto } from '@/store/obituary/obituary.service';
import { keepPreviousData, useMutation, useQuery } from '@tanstack/react-query';

const CardGuestPage = () => {
  const searchParams = useSearchParams();
  const router = useRouter();

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

  const { mutate: mutateIncreaseConnection } = useMutation({
    mutationFn: ({ id }: { id: string }) => increaseCountViewApi(id),
    onSuccess: () => {},
  });

  const [isOpenShared, setIsOpenShared] = useState<boolean>(false);

  useEffect(() => {
    if (searchParams.get('isSuccess') && obituaryData) {
      if (searchParams.get('isSuccess') === 'TRUE') {
        alert('조의금 전달 성공');
      } else {
        alert('조의금 전달 실패');
      }
      const updatedSearchParams = new URLSearchParams(searchParams.toString());
      updatedSearchParams.delete('isSuccess');

      router.replace(`?${updatedSearchParams.toString()}`);
    }
  }, [searchParams, obituaryData, router]);

  useEffect(() => {
    const hasVisitedSession = !!sessionStorage.getItem(`viewed_${id}`);
    if (!hasVisitedSession && id) {
      mutateIncreaseConnection({ id });
      sessionStorage.setItem(`viewed_${id}`, 'true');
    }
  }, [id]);

  return (
    <div className='px-[20px] mt-5 lg:px-[50px]'>
      <div className='flex justify-between items-center'>
        <img className='logo-green h-[40px]' src='/images/logo.svg' alt='logo' />
        <a href={`card-guest/condolence?id=${id}`} className='btn-primary-obituary !w-[110px] !h-[35px] !text-[14px]'>
          조의금 전달
        </a>
      </div>
      <div className={`flex flex-col items-center mt-3`}>
        <div className='w-full max-w-[480px] flex flex-col justify-center'>
          <Obituary isOwner={false} data={obituaryData} />
          <ObituaryImages data={obituaryData} />
          <FuneralInformation data={obituaryData} />
          <Message id={id} />
        </div>
      </div>

      <SharedModal
        isOpen={isOpenShared}
        onClose={() => {
          setIsOpenShared(false);
        }}
      />
    </div>
  );
};

export default CardGuestPage;
