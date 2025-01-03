'use client';

import React, { useState, useCallback } from 'react';
import { Input, Card, List, Modal, Typography, Row, Col, Pagination } from 'antd';
import styled from 'styled-components';
import { BASE_HEXAGRAMS } from '../data/base';
import { Hexagram } from '../types';
import { HexagramDisplay } from './HexagramDisplay';
import { HexagramDetail } from './HexagramDetail';

const { Search } = Input;
const { Text } = Typography;

const SearchContainer = styled.div`
  margin: 20px 0;
  max-width: 1200px;
  width: 100%;
`;

const HexagramCard = styled(Card)`
  cursor: pointer;
  transition: all 0.3s ease;
  height: 100%;
  
  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
`;

const PAGE_SIZE = 12;

export const HexagramSearch: React.FC = () => {
  const [searchText, setSearchText] = useState('');
  const [selectedHexagram, setSelectedHexagram] = useState<Hexagram | null>(null);
  const [currentPage, setCurrentPage] = useState(1);

  // 确保卦象数组按序号排序，从第1卦开始
  const sortedHexagrams = Object.values(BASE_HEXAGRAMS)
    .sort((a, b) => (a.sequence || 0) - (b.sequence || 0))
    .filter(hexagram => hexagram.sequence > 0);

  const filteredHexagrams = sortedHexagrams.filter((hexagram) => {
    if (!searchText) return true;
    
    const searchLower = searchText.toLowerCase();
    const sequenceStr = hexagram.sequence?.toString() || '';
    
    return (
      hexagram.name.toLowerCase().includes(searchLower) ||
      sequenceStr.includes(searchLower) ||
      hexagram.nature.toLowerCase().includes(searchLower) ||
      hexagram.description.toLowerCase().includes(searchLower) ||
      hexagram.meaning.general.toLowerCase().includes(searchLower)
    );
  });

  const currentHexagrams = filteredHexagrams.slice(
    (currentPage - 1) * PAGE_SIZE,
    currentPage * PAGE_SIZE
  );

  const handleSearch = useCallback((value: string) => {
    setSearchText(value);
    setCurrentPage(1);
  }, []);

  const handlePageChange = useCallback((page: number) => {
    setCurrentPage(page);
  }, []);

  return (
    <SearchContainer>
      <Search
        placeholder="输入卦名、序号、性质或关键词搜索"
        allowClear
        enterButton
        size="large"
        onSearch={handleSearch}
        onChange={(e) => handleSearch(e.target.value)}
        style={{ marginBottom: 24 }}
      />

      <Row gutter={[16, 16]}>
        {currentHexagrams.map((hexagram) => (
          <Col key={hexagram.key} xs={24} sm={12} md={8} lg={6}>
            <HexagramCard
              onClick={() => setSelectedHexagram(hexagram)}
              bodyStyle={{ padding: '12px' }}
            >
              <div style={{ textAlign: 'center' }}>
                <HexagramDisplay hexagram={hexagram} showDetails={false} />
                <Text strong style={{ fontSize: '16px', display: 'block', marginTop: 8 }}>
                  第{hexagram.sequence}卦 {hexagram.name}
                </Text>
                <Text type="secondary" style={{ fontSize: '14px' }}>
                  {hexagram.nature}
                </Text>
              </div>
            </HexagramCard>
          </Col>
        ))}
      </Row>

      <div style={{ textAlign: 'center', marginTop: 24 }}>
        <Pagination
          current={currentPage}
          total={filteredHexagrams.length}
          pageSize={PAGE_SIZE}
          onChange={handlePageChange}
          showSizeChanger={false}
        />
      </div>

      <Modal
        title={`第${selectedHexagram?.sequence}卦 ${selectedHexagram?.name} - 详解`}
        open={!!selectedHexagram}
        onCancel={() => setSelectedHexagram(null)}
        width={800}
        footer={null}
      >
        {selectedHexagram && <HexagramDetail hexagram={selectedHexagram} />}
      </Modal>
    </SearchContainer>
  );
}; 