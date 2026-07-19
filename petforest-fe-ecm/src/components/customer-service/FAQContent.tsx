'use client';
import { faqKeys, getPostDetailApi, getPostListApi } from '@/store/customer-service/customer-service.api';
import { announcementDetailRespToDto, faqRespToDto } from '@/store/customer-service/customer-service.service';
import { FrequentlyAskedKeys } from '@/store/customer-service/dto/customer-service.dto';
import { FaqREQ } from '@/store/customer-service/request/customer-service.request';
import { FAQ_FILTERS, FAQ_TEXT } from '@/utils/constant/customer-service/customer-service.constant';
import { POST_CATEGORIES } from '@/utils/enums/customer-service.enum';
import { AddIcon, MinusIcon } from '@chakra-ui/icons';
import { Accordion, AccordionButton, AccordionItem, AccordionPanel } from '@chakra-ui/react';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { ListData } from '../list-data/ListData';

const FAQContent = () => {
  const [activeTag, setActiveTag] = useState<FrequentlyAskedKeys>(POST_CATEGORIES.FREQUENTLY_ASKED);
  const [faqDetailId, setFaqDetailId] = useState<number>(0);

  const [filter] = useState<FaqREQ>({
    filter: JSON.stringify({ ['visibility.in']: ['RELEASE'] }),
  });

  const { data: faqList } = useQuery({
    queryKey: faqKeys.list(filter),
    queryFn: () => getPostListApi(filter),
    select: (response) => {
      return { ...response.data, data: faqRespToDto(response.data.data) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: faqDetailData } = useQuery({
    queryKey: faqKeys.detail(faqDetailId),
    queryFn: () => getPostDetailApi(faqDetailId),
    select: (response) => {
      return response.data.data && announcementDetailRespToDto(response.data.data);
    },
    enabled: faqDetailId !== 0,
  });

  return (
    <div className='sec'>
      <div className='lmt maxw flex flex-col items-center justify-center mt-[50px] md:mt-[100px]'>
        <p className='text-[25px] text-green-main leading-[1.5] font-bold mb-[35px] md:text-[32px] md:mb-[40px]'>자주하는 질문</p>
        <ListData
          data={FAQ_FILTERS}
          keyExtractor={(item) => item}
          renderItem={(faq: any) => (
            <div className={`faq-category ${activeTag === faq ? 'active' : ''}`} onClick={() => setActiveTag(faq)}>
              {FAQ_TEXT[faq as keyof typeof FAQ_TEXT]}
            </div>
          )}
          styleList='flex flex-wrap justify-center mb-[30px] !gap-[5px] faq-category-list md:mb-[47px] md:!gap-[7px]'
        />
        {faqList?.data[activeTag] && faqList?.data[activeTag].length > 0 && (
          <Accordion key={activeTag} allowToggle className='w-full md:w-[60%]'>
            {faqList?.data[activeTag].map((faq, index) => (
              <AccordionItem
                key={index}
                borderBottom='none'
                onClick={() => {
                  setFaqDetailId(faq.id);
                }}>
                {({ isExpanded }) => (
                  <>
                    <h2>
                      <AccordionButton
                        className={`flex justify-between gap-2 px-[8px] py-[15px] hover:bg-ligt-green md:py-[20px] ${isExpanded && 'bg-ligt-green'}`}>
                        <div className='flex flex-col items-start md:flex-row md:gap-[30px] md:items-center'>
                          <span className='text-left text-[12px] text-green-teal font-bold md:text-green-envy md:text-[13px] md:w-[80px]'>
                            {faq.category && FAQ_TEXT[faq.category as keyof typeof FAQ_TEXT]}
                          </span>
                          {/* md:text-[17.5px] */}
                          <p className='text-left text-[15px] font-medium text-black-nero md:text-[16px]'>{faq?.title}</p>
                        </div>
                        {isExpanded ? (
                          <MinusIcon className='w-[12px] h-[12px] md:w-[16px] md:h-[16px]' color='#0D4221' />
                        ) : (
                          <AddIcon className='w-[12px] h-[12px] md:w-[16px] md:h-[16px]' color='#0D4221' />
                        )}
                      </AccordionButton>
                    </h2>
                    <AccordionPanel
                      className={`text-left text-[15px] text-grey-green px-0 pt-[10px] pb-[15px] md:pl-[118px] md:pt-[16px] md:pb-[24px] transition-[height] ease-in-out duration-300 overflow-hidden ${
                        isExpanded ? 'max-h-[500px] opacity-100' : 'max-h-0 opacity-0'
                      }`}>
                      <div className='text-left' dangerouslySetInnerHTML={{ __html: faqDetailData?.content as string }} />
                    </AccordionPanel>
                    {index === faqList.data[activeTag].length - 1 && (
                      <div
                        className={`transition-[border-bottom] duration-300 ease-in-out border-b-[1px] opacity-50 border-light-grey `}
                      />
                    )}
                  </>
                )}
              </AccordionItem>
            ))}
          </Accordion>
        )}
      </div>
    </div>
  );
};

export default FAQContent;
