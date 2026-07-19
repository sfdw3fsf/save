'use client';
import { ListData } from '@/components/list-data/ListData';
import { getObituaryMessagesApi, obituaryMessagesKeys } from '@/store/obituary/obituary.api';
import { obituaryMessagesRespToDto } from '@/store/obituary/obituary.service';
import { orString } from '@/utils/parser/io.parser';
import { ChevronLeftIcon } from '@chakra-ui/icons';
import { Card } from '@chakra-ui/react';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useRouter, useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import '../../obituary.css';

type DataType = {
  id: number;
  from: string;
  createAt: string;
  content: string;
};

const page = () => {
  const router = useRouter();
  const searchParams = useSearchParams();

  const id: string = useMemo(() => {
    return orString(searchParams.get('id'));
  }, [searchParams]);

  const { data: obituaryMessagesData } = useQuery({
    queryKey: obituaryMessagesKeys.list({ id }),
    queryFn: () => getObituaryMessagesApi(id),
    select: (response) => {
      return response.data.data.map(obituaryMessagesRespToDto);
    },
    placeholderData: keepPreviousData,
    enabled: !!id,
  });

  const messageData = useMemo(() => {
    if (!obituaryMessagesData) return [];
    return obituaryMessagesData.map((item) => ({
      id: item.id,
      from: item.author,
      createAt: item.createdAt,
      content: item.content,
    }));
  }, [obituaryMessagesData]);

  const renderMessages = (data: DataType) => (
    <a href={`guest-list/edit?id=${id}&messageId=${data.id}`}>
      <Card className='w-full px-5 pt-5 pb-[15px] text-left'>
        <div className='flex items-center justify-between'>
          <span className='text-[15px] text-grey-mist'>
            From. <span className='font-bold text-black-nero'>{data.from}</span>
          </span>
          <span className='text-[12px] text-grey-mist'>{data.createAt}</span>
        </div>
        <p className='mt-[7px]'>{data.content}</p>
      </Card>
    </a>
  );

  return (
    <div className='px-[20px] lg:px-[50px] flex flex-col text-center items-center'>
      <div className='flex items-center mt-5 w-full relative'>
        <button
          className='absolute left-0 top-2/4 -translate-y-2/4'
          onClick={() => {
            router.back();
          }}>
          <ChevronLeftIcon w={7} h={7} />
        </button>

        <div className=' w-full text-[17px] font-bold text-green-main'>기억의 숲, 방명록</div>
      </div>
      <p className='text-[14px] text-grey-green'>소중한 마음들이 모여있는 곳입니다.</p>

      <div className='w-full max-w-[480px]'>
        <div className='mt-[15px] flex items-center justify-between'>
          <span className='text-[14px] font-semibold'>전체 {messageData.length}건</span>
          <a
            href={`guest-list/create?id=${id}`}
            className={`bg-green-main text-white px-3 py-1 rounded-[4px] text-[14px] cursor-pointer`}>
            방명록 작성
          </a>
        </div>
        <ListData<DataType>
          data={messageData}
          keyExtractor={(item) => item.id}
          renderItem={renderMessages}
          numberOfColumns={1}
          styleContainer='mt-3'
        />
      </div>
    </div>
  );
};

export default page;
