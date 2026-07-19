'use client';
import { getObituaryApi, obituaryKeys } from '@/store/obituary/obituary.api';
import { obituaryRespToDto } from '@/store/obituary/obituary.service';
import { orString } from '@/utils/parser/io.parser';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import FuneralInformation from '../components/FuneralInformation';
import Message from '../components/Message';
import Obituary from '../components/Obituary';
import ObituaryImages from '../components/ObituaryImages';

const ObituaryCreatorPage = () => {
  const searchParams = useSearchParams();

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

  return (
    <div className='px-[20px] mt-5 lg:px-[50px]'>
      <img className='logo-green h-[40px]' src='/images/logo.svg' alt='logo' />
      <div className={`flex flex-col items-center mt-3`}>
        <div className='w-full max-w-[480px] flex flex-col justify-center'>
          <Obituary data={obituaryData} />
          <ObituaryImages data={obituaryData} />
          <FuneralInformation data={obituaryData} />
          <Message data={obituaryData} id={id} />
        </div>
      </div>
    </div>
  );
};

export default ObituaryCreatorPage;
